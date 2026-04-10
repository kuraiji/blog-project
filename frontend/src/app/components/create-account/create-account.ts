import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Button} from '@components/button/button';
import {
  MAX_EMAIL_LENGTH,
  MAX_HANDLE_LENGTH,
  MAX_PASSWORD_LENGTH,
  MIN_PASSWORD_LENGTH,
  PASSWORD_REGEX
} from '@common/constraints';
import {passwordMatchValidator} from '@common/validators';
import {validateFormGroup} from '@common/helper';
import {User} from '@services/user';
import {CreateUserRequest} from '@common/types';
import {catchError} from 'rxjs';

@Component({
  selector: 'app-create-account',
  imports: [ReactiveFormsModule, Button],
  templateUrl: './create-account.html',
  styleUrl: './create-account.css',
})
export class CreateAccount {
  userService = inject(User);
  createAccountForm = new FormGroup({
    handle: new FormControl('', [
      Validators.required,
      Validators.maxLength(MAX_HANDLE_LENGTH),
    ]),
    email: new FormControl('', [
      Validators.required,
      Validators.email,
      Validators.maxLength(MAX_EMAIL_LENGTH),
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(MIN_PASSWORD_LENGTH),
      Validators.maxLength(MAX_PASSWORD_LENGTH),
      Validators.pattern(PASSWORD_REGEX)
    ]),
    confirmPassword: new FormControl('', [
      Validators.required
    ])
  }, { validators: passwordMatchValidator });

  get handle() {
    return this.createAccountForm.get('handle');
  }

  get email() {
    return this.createAccountForm.get('email');
  }

  get password() {
    return this.createAccountForm.get('password');
  }

  get confirmPassword() {
    return this.createAccountForm.get('confirmPassword');
  }

  onSubmit = (): void => {
    const request = new CreateUserRequest(
      this.createAccountForm.get('handle')?.value!,
      this.createAccountForm.get('email')?.value!,
      this.createAccountForm.get('password')?.value!
    );
    this.userService.createUser(request).pipe(
      catchError((error) => {
        console.log(error);
        throw error;
      })
    ).subscribe( user => console.log(user));
  }

  onIsNotValid = (): boolean => {
    return !validateFormGroup(this.createAccountForm);
  }

  onKeyUp = (_: Event): void => {
    const confirmPassword = document.getElementById('confirmPassword') as HTMLInputElement;
    if (!confirmPassword) return;
    if(this.createAccountForm.hasError('passwordMismatch')) {
      confirmPassword.setCustomValidity("Passwords do not match");
    } else {
      confirmPassword.setCustomValidity("");
    }
  }

  protected readonly PASSWORD_REGEX = PASSWORD_REGEX;
}
