package com.rip.roomies.sql;

import com.rip.roomies.models.Group;
import com.rip.roomies.models.User;
import com.rip.roomies.util.Exceptions;
import com.rip.roomies.util.InfoStrings;
import com.rip.roomies.util.SQLStrings;

import java.sql.ResultSet;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * A class that contains static methods for executing database commands relating to adding objects
 * or properties to existing objects.
 */
public class SQLAdd {
	private static final Logger log = Logger.getLogger(SQLAdd.class.getName());

	/**
	 * Adds an existing user to an existing group.
	 * @param users An array of users to add
	 * @param group The group to add users to
	 * @return The group that the users were added to
	 */
	public static Group addUsersToGroup(User[] users, Group group) {
		ResultSet rs;
		String usersString = "";

		try {
			// Turn users array into a delineated string
			for (User user : users) {
				usersString += user.getId();
				usersString += SQLStrings.LIST_DELINEATOR;
			}

			// Can only take max length of 1000
			if (usersString.length() > 1000) {
				return null;
			}

			// Log adding user to group
			log.info(InfoStrings.ADD_USERS_TO_GROUP_SQL);

			// Execute SQL
			rs = SQLQuery.execute(String.format(Locale.US, SQLStrings.ADD_USERS_TO_GROUP,
					group.getId(), usersString));
			rs.next();

			// If no rows, then adding failed
			if (rs.getRow() == 0) {
				log.info(InfoStrings.ADD_USERS_TO_GROUP_FAILED);
				return null;
			}

			// Otherwise, success
			else {
				// Get results of SQL statement. Columns are ID, name, and description.
				int resultID = rs.getInt("ID");
				String resultName = rs.getString("Name");
				String resultDescription = rs.getString("Description");

				//debug statement
				log.info(String.format(Locale.US, InfoStrings.ADD_USERS_TO_GROUP_SUCCESSFULL,
						resultID, resultName, resultDescription));

				// Return a new group object
				return new Group(resultID, resultName, resultDescription, users);
			}
		}
		catch (Exception e) {
			// Log and return null on exception
			log.severe(Exceptions.stacktraceToString(e));
			return null;
		}
	}
}
