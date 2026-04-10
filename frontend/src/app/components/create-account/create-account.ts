import {Component} from '@angular/core';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {Button} from '@components/button/button';

@Component({
  selector: 'app-create-account',
  imports: [ReactiveFormsModule, Button],
  templateUrl: './create-account.html',
  styleUrl: './create-account.css',
})
export class CreateAccount {
  handle = new FormControl('');
  email = new FormControl('');
  password = new FormControl('');
  confirmPassword = new FormControl('');
}
