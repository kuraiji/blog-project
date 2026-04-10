export function showDialog(modalId: string){
  const de = document.getElementById(modalId) as HTMLDialogElement;
  de.showModal();
}
