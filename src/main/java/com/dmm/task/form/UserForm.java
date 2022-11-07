package com.dmm.task.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserForm {

	@Size(min = 1,max = 200)
    private String name;

	@Size(min = 1,max = 200)
	@Email
    private String email;
}