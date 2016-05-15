package com.rip.roomies.controllers;

import android.os.AsyncTask;

import com.rip.roomies.functions.CreateGroupFunction;
import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.InfoStrings;

import java.util.Locale;
import java.util.logging.Logger;

/**
 * The controller to handle group related events.
 */
public class GroupController {
	private static final Logger log = Logger.getLogger(GroupController.class.getName());

	private static GroupController controller;

	/**
	 * Gets the static controller singleton object for this controller.
	 * @return The singleton controller
	 */
	public static GroupController getController() {
		if (controller == null) {
			controller = new GroupController();
		}

		return controller;
	}

	/**
	 * Creates a new group with the parameters specified, and adds the current user
	 * along with all the invitees afterwards.
	 * @param funct The funct to post the results to
	 * @param name The name of the group to create
	 * @param description The description of the group to create
	 * @param invitees The list of invitees exclusing the active user
	 */
	public void createGroup(final CreateGroupFunction funct, final String name,
	                        final String description, final User[] invitees) {
		// Create and run a new thread
		new AsyncTask<Void, Void, Group>() {
			@Override
			protected Group doInBackground(Void... params) {
				// Debug user entered fields
				log.info(String.format(Locale.US, InfoStrings.CREATEGROUP_CONTROLLER,
						name, description));

				// Create request group and get response from createGroup()
				Group request = new Group(name, description);
				Group response = request.createGroup();

				// If fail, call fail callback. Otherwise, call success callback
				if (response == null) {
					return null;
				}
				else {
					// Add the current user and all invitees to the newly created group
					User[] users = new User[invitees.length + 1];
					System.arraycopy(invitees, 0, users, 0, invitees.length);
					users[invitees.length] = User.getActiveUser();

					String usersString = "[";
					for (int i = 0; i < users.length; ++i) {
						usersString += users[i].getFirstName() + " " + users[i].getLastName();

						if (i != users.length - 1) {
							usersString += ", ";
						}
					}
					usersString += "]";

					log.info(String.format(Locale.US, InfoStrings.ADD_USERS_TO_GROUP_CONTROLLER,
							response.getId(), response.getName(), usersString));

					return response.addUsers(users);
				}
			}

			@Override
			protected void onPostExecute(Group response) {
				// If this call fails, whole thing fails
				if (response == null) {
					funct.createGroupFail();
				}

				// Otherwise, print success
				else {
					Group.setActiveGroup(response);
					funct.createGroupSuccess(response);
				}
			}
		}.execute();
	}
}
