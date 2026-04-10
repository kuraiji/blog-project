import {Component, input, output} from '@angular/core';
import {ButtonType} from '@enums/button-type';
import {ButtonStyle} from '@enums/button-style';

@Component({
  selector: 'app-button',
  imports: [],
  templateUrl: './button.html',
  styleUrl: './button.css',
})
export class Button {
  readonly type = input<ButtonType>(ButtonType.Neutral);
  readonly style = input<ButtonStyle>(ButtonStyle.Normal);
  readonly isSubmit = input<boolean>(false);
  readonly isDisabled = input<boolean>(false);
  readonly onClickEvent = output<void>();
  onClick = (): void => {
    this.onClickEvent.emit();
  }
}
