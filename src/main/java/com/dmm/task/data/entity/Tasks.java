package com.dmm.task.data.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Tasks {
	@Id
	public int id;
	public String title;
	public String name;
	public String text;
	LocalDate day;
	public boolean done;
}
