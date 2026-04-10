import {FormGroup} from '@angular/forms';

export function showDialog(modalId: string){
  const de = document.getElementById(modalId) as HTMLDialogElement;
  de.showModal();
}

export function validateFormGroup(formGroup: FormGroup): boolean{
  if(!formGroup.valid) return false;
  for(let key in formGroup.controls){
    const control = formGroup.get(key);
    if(!control || control.pristine || control.invalid) return false;
  }
  return true;
}
