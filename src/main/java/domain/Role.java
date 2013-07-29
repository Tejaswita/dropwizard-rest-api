package domain;

public enum Role {

	EMPLOYEE("Employee"), MANAGER("Manager");

	private final String role;

	private Role(String roleName) {
		this.role = roleName;
	}

	public static Role toRole(String role) {
		if (role.equalsIgnoreCase(EMPLOYEE.name())) {
			return EMPLOYEE;
		} else
			return MANAGER;
	}
}
