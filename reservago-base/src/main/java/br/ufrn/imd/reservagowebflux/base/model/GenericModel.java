package br.ufrn.imd.reservagowebflux.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public abstract class GenericModel<PK extends Serializable> {

	@CreatedDate
	@JsonDeserialize(
			using = LocalDateTimeDeserializer.class
	)
	@JsonSerialize(
			using = LocalDateTimeSerializer.class
	)
	@JsonIgnore
	private LocalDateTime creationDate = LocalDateTime.now();

	@LastModifiedDate
	@JsonDeserialize(
			using = LocalDateTimeDeserializer.class
	)
	@JsonSerialize(
			using = LocalDateTimeSerializer.class
	)
	@JsonIgnore
	private LocalDateTime modificationDate;

	@LastModifiedDate
	@JsonDeserialize(
			using = LocalDateTimeDeserializer.class
	)
	@JsonSerialize(
			using = LocalDateTimeSerializer.class
	)
	@JsonIgnore
	private LocalDateTime exclusionDate;
	private @NotNull Boolean active = true;

	public abstract PK getId();

	public abstract void setId(PK id);

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}

	public LocalDateTime getExclusionDate() {
		return exclusionDate;
	}

	public void setExclusionDate(LocalDateTime exclusionDate) {
		this.exclusionDate = exclusionDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
