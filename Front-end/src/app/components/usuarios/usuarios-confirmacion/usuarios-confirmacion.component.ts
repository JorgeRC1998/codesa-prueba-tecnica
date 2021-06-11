import { Component, OnInit, Input, Optional, Inject  } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-usuarios-confirmacion',
  templateUrl: './usuarios-confirmacion.component.html',
  styleUrls: ['./usuarios-confirmacion.component.css']
})
export class UsuariosConfirmacionComponent implements OnInit {

  mensaje: string;

  constructor(
    private dialogRef: MatDialogRef<UsuariosConfirmacionComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any
  ) { 
    this.mensaje = data.data.mensaje;
  }

  ngOnInit(): void {
  }

  rechazar(flag: boolean){
    this.dialogRef.close({resultado: flag}); 
  }

  aceptar(flag: boolean){
    this.dialogRef.close({resultado: flag}); 
  }

}
