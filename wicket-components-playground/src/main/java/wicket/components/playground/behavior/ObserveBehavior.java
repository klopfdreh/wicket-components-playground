package wicket.components.playground.behavior;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;

public abstract class ObserveBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	public enum Event {
		click,
		change,
		mouseover,
		mouseout,
		keydown,
		load
	}

	private Event event = Event.click;

	@Override
	protected void respond(AjaxRequestTarget target) {
		String data = RequestCycle.get().getRequest().getPostParameters().getParameterValue("data").toString();
		try {
			this.processResponse(target, data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		component.setOutputMarkupId(true);
		String mid = component.getMarkupId();
		String script = String.format(this.readScript(), mid, mid, this.event.name(), mid, mid, mid, mid, this.getCallbackUrl(), mid);
		response.render(OnDomReadyHeaderItem.forScript(script));
	}

	@SuppressWarnings("resource")
	private String readScript() {
		Scanner scanner = null;
		try {
			String className = ObserveBehavior.class.getSimpleName() + ".js";
			InputStream resourceAsStream = ObserveBehavior.class.getResourceAsStream(className);
			scanner = new Scanner(resourceAsStream, "UTF-8").useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	public ObserveBehavior forEvent(Event event) {
		this.event = event;
		return this;
	}

	public String getOldEventValueAsString(String data, String key) throws JSONException {
		return this.getPlainObject(data, key, "oldEvent").toString();
	}

	public boolean getOldEventValueAsBoolean(String data, String key) throws JSONException {
		return Boolean.parseBoolean(this.getPlainObject(data, key, "oldEvent").toString());
	}

	public int getOldEventValueAsInt(String data, String key) throws JSONException {
		return Integer.parseInt(this.getPlainObject(data, key, "oldEvent").toString());
	}

	public long getOldEventValueAsLong(String data, String key) throws JSONException {
		return Long.parseLong(this.getPlainObject(data, key, "oldEvent").toString());
	}

	public double getOldEventValueAsDouble(String data, String key) throws JSONException {
		return Double.parseDouble(this.getPlainObject(data, key, "oldEvent").toString());
	}

	public String getNewEventValueAsString(String data, String key) throws JSONException {
		return this.getPlainObject(data, key, "newEvent").toString();
	}

	public boolean getNewEventValueAsBoolean(String data, String key) throws JSONException {
		return Boolean.parseBoolean(this.getPlainObject(data, key, "newEvent").toString());
	}

	public int getNewEventValueAsInt(String data, String key) throws JSONException {
		return Integer.parseInt(this.getPlainObject(data, key, "newEvent").toString());
	}

	public long getnewEventValueAsLong(String data, String key) throws JSONException {
		return Long.parseLong(this.getPlainObject(data, key, "newEvent").toString());
	}

	public double getnewEventValueAsDouble(String data, String key) throws JSONException {
		return Double.parseDouble(this.getPlainObject(data, key, "newEvent").toString());
	}

	public String getText(String data) throws JSONException {
		JSONObject object = new JSONObject(data);
		return object.get("value").toString();
	}

	public String getHTML(String data) throws JSONException {
		JSONObject object = new JSONObject(data);
		return object.get("html").toString();
	}

	public Object getPlainObject(String data, String key, String eventObjectName) throws JSONException {
		JSONObject object = new JSONObject(data);
		JSONObject jsonObject = object.getJSONObject(eventObjectName);
		return this.getJsonObject(jsonObject, key);
	}

	public Object getJsonObject(JSONObject object, String key) throws JSONException {
		Iterator<?> keys = object.keys();
		Object plainObject = null;
		while (keys.hasNext()) {
			String jsonKey = (String) keys.next();
			if (object.get(jsonKey) instanceof JSONObject) {
				plainObject = this.getJsonObject(object, jsonKey);
			}
			if (jsonKey.equals(key)) {
				plainObject = object.get(jsonKey);
			}
		}
		return plainObject;
	}

	public abstract void processResponse(AjaxRequestTarget target, String data) throws JSONException;
}
