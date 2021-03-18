package com.revature.models;

import java.sql.Timestamp;

public class Reimbursement {
	
	private int id;
	private int authorId;
	private Integer resolverId;
	private double amount;
	private String description;
	private Timestamp submitted;
	private Timestamp resolved;
	private int status;
	private int type;
	
	public Reimbursement() {
		
	}

	public Reimbursement(int id, int authorId, Integer resolverId, double amount, String description, Timestamp submitted,
			Timestamp resolved, int status, int type) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.amount = amount;
		this.description = description;
		this.submitted = submitted;
		this.resolved = resolved;
		this.status = status;
		this.type = type;
	}

	public Reimbursement(int authorId, Integer resolverId, double amount, String description, Timestamp submitted,
			Timestamp resolved, int status, int type) {
		super();
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.amount = amount;
		this.description = description;
		this.submitted = submitted;
		this.resolved = resolved;
		this.status = status;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public Integer getResolverId() {
		return resolverId;
	}

	public void setResolverId(Integer resolverId) {
		this.resolverId = resolverId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + authorId;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + resolverId;
		result = prime * result + status;
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (authorId != other.authorId)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolverId != other.resolverId)
			return false;
		if (status != other.status)
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", authorId=" + authorId + ", resolverId=" + resolverId + ", amount="
				+ amount + ", description=" + description + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", status=" + status + ", type=" + type + "]";
	}

	

}
