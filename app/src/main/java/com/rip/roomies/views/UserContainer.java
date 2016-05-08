package com.rip.roomies.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.logging.Logger;

/**
 * This class represents a container for multiple UserView objects that can
 * be displayed in a dynamic group.
 */
public class UserContainer extends LinearLayout {
	private static final Logger log = Logger.getLogger(UserContainer.class.getName());

	/**
	 * @see android.view.View(Context)
	 */
	public UserContainer(Context context) {
		super(context);
	}

	/**
	 * @see android.view.View(Context, AttributeSet)
	 */
	public UserContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @see android.view.View(Context, AttributeSet, int)
	 */
	public UserContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Adds a new user to the UserContainer at the end of the list.
	 *
	 * @param newUser The new User to add
	 */
	public void addUser(User newUser) {
		log.info(String.format(InfoStrings.CONTAINER_ADD,
				UserView.class.getSimpleName(), UserContainer.class.getSimpleName()));

		UserView userView = new UserView(getContext());
		userView.setUser(newUser);
		addView(userView);
	}
}