package com.rip.roomies.models;

import com.rip.roomies.sql.SQLCreate;
import com.rip.roomies.sql.SQLGet;
import com.rip.roomies.sql.SQLModify;
import com.rip.roomies.sql.SQLRemove;
import com.rip.roomies.util.InfoStrings;

import java.util.logging.Logger;

/**
 * This class defines a duty that requires an action to complete.
 */
public class Duty extends Task<DutyLog, Duty> {
	private static final Logger log = Logger.getLogger(Duty.class.getName());

	//------- CONSTRUCTORS -------//

	/** @inheritDoc **/
	public Duty(int id) {
		super(id);
	}

	/** @inheritDoc **/
	public Duty(String name, String description, int groupId, User[] users) {
		super(name, description, groupId, users);
	}

	/** @inheritDoc **/
	public Duty(int id, String name, String description, User[] users) {
		super(id, name, description, users);
	}

	/** @inheritDoc **/
	public Duty(int id, String name, String description, int groupId, User assignee, User[] users) {
		super(id, name, description, groupId, assignee, users);
	}

	//------- DATABASE METHODS -------//

	/** @inheritDoc **/
	@Override
	public Duty create() {
		log.info(InfoStrings.CREATE_DUTY_MODEL);
		return SQLCreate.createDuty(this);
	}

	/** @inheritDoc **/
	@Override
	public Duty modify() {
		log.info(InfoStrings.MODIFY_DUTY_MODEL);
		return SQLModify.modifyDuty(this);
	}

	/** @inheritDoc **/
	@Override
	public Duty remove() {
		log.info(InfoStrings.REMOVE_DUTY_MODEL);
		return SQLRemove.removeDuty(this);
	}

	/** @inheritDoc **/
	@Override
	public DutyLog complete() {
		log.info(InfoStrings.COMPLETE_DUTY_MODEL);
		return SQLModify.completeDuty(this);
	}

	/** @inheritDoc **/
	@Override
	public Duty getRotation() {
		log.info(InfoStrings.GET_ROTATION_MODEL);
		return SQLGet.getDutyUsers(this);
	}
}
