import {Component, input} from '@angular/core';
import {ButtonType} from '@enums/button-type';
import {ButtonStyle} from '@enums/button-style';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-link-button',
  imports: [RouterLink],
  templateUrl: './link-button.html',
  styleUrl: './link-button.css',
})
export class LinkButton {
  type = input<ButtonType>(ButtonType.Neutral);
  style = input<ButtonStyle>(ButtonStyle.Normal);
  url = input.required<string>();
}
