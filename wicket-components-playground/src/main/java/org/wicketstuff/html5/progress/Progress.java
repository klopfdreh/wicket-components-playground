package org.wicketstuff.html5.progress;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;

public abstract class Progress extends WebComponent
{

	private static final long serialVersionUID = 1L;

	private AtomicInteger value = new AtomicInteger(0);

	private AtomicInteger max = new AtomicInteger(1);

	private Duration duration;

	private AtomicBoolean process = new AtomicBoolean(false);

	public Progress(String id, Duration duration)
	{
		this(id, null, duration);
	}

	public Progress(String id, IModel<?> model, Duration duration)
	{
		super(id, model);
		this.duration = duration;
		setOutputMarkupId(true);
	}

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		checkComponentTag(tag, "progress");
		super.onComponentTag(tag);
		tag.put("value", value.get());
		tag.put("max", max.get());
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		final AjaxSelfUpdatingTimerBehavior ajaxSelfUpdatingTimerBehavior = new AjaxSelfUpdatingTimerBehavior(
			duration)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onPostProcessTarget(AjaxRequestTarget target)
			{
				target.add(Progress.this);
				// Check if the process started
				if (process.get())
				{
					// If the task has been finished and the value exceeds the
					// max value stop the self updating timer behavior and call
					// done so that the user is able to refresh its components
					if (value.get() >= max.get())
					{
						stop(target);
						done(target);
					}
				}
			}
		};
		add(ajaxSelfUpdatingTimerBehavior);
		Thread refreshingThread = new Thread()
		{
			@Override
			public void run()
			{
				// Run the init process method in a separate thread so that
				// the actual thread is moved on and update process value is triggert
				// to check the state
				Thread processThread = new Thread()
				{
					@Override
					public void run()
					{
						initProcess();
					};
				};
				processThread.start();

				// The user override the method updateProcessValue and each time an increased
				// number is going to be returned
				while (value.get() <= max.get())
				{
					try
					{
						Thread.sleep(duration.getMilliseconds());
					}
					catch (InterruptedException e)
					{
						// OUCHY!!
					}
					value.set(updateProcessValue());
					process.set(true);
				}
			}
		};
		refreshingThread.start();
	}

	protected abstract void initProcess();

	protected abstract int updateProcessValue();

	protected void done(AjaxRequestTarget target)
	{
	}

	public int getValue()
	{
		return value.get();
	}

	public void setValue(int value)
	{
		this.value.set(value);
	}

	public int getMax()
	{
		return max.get();
	}

	public void setMax(int max)
	{
		this.max.set(max);
	}
}
