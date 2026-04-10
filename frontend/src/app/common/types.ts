export class CreateUserRequest {

  public handle: string;

  public email: string;

  public password: string;

  constructor(handle: string, email: string, password: string) {
    this.handle = handle;
    this.email = email;
    this.password = password;
  }

}

export class Role {

  public id: number;

  public name: string;

  constructor(id: number, name: string) {
    this.id = id;
    this.name = name;
  }
}

export class User {

  public id: number;

  public handle: string;

  public email: string;

  public role: Role;

  constructor(handle: string, email: string, role: Role) {
    this.handle = handle;
    this.email = email;
    this.role = role;
    this.id = role.id;
  }
}
