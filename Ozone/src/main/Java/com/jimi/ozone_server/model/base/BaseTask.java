package com.jimi.ozone_server.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTask<M extends BaseTask<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setTypeId(java.lang.Long typeId) {
		set("type_id", typeId);
		return (M)this;
	}
	
	public java.lang.Long getTypeId() {
		return getLong("type_id");
	}

	public M setDeleted(java.lang.Boolean deleted) {
		set("deleted", deleted);
		return (M)this;
	}
	
	public java.lang.Boolean getDeleted() {
		return get("deleted");
	}

}
