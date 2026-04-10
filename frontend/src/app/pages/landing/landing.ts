import { Component } from '@angular/core';
import {ButtonStyle} from '@enums/button-style';
import {Button} from '@components/button/button';
import {showDialog} from '@common/helper';
import {CreateAccount} from '@components/create-account/create-account';

@Component({
  selector: 'app-landing',
  imports: [Button, CreateAccount],
  templateUrl: './landing.html',
  styleUrl: './landing.css',
})
export class Landing {
  protected readonly ButtonStyle = ButtonStyle;
  protected readonly createAccountId = "create_account";
  protected readonly signInId = "sign_in";
  showCreateAccount = () => {
    showDialog(this.createAccountId);
  };
  showSignIn = () => {
    showDialog(this.signInId);
  }
}
