package com.nickaknudson.mva.callbacks;

/**
 * @author nick
 */
public interface ManagedCallback extends Callback {
	/**
	 * A callback that can optionally be called once or indefinitely
	 * @return if the callback should only be executed once
	 */
	public boolean once();
	/**
	 * A callback that can optionally request previous events be replayed for it
	 * @return if previous events should be replayed for the callback
	 */
	public boolean replay();
}
