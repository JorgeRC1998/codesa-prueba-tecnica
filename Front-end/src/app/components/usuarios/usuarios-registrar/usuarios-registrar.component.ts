import { Component, OnInit, Input, Optional, Inject  } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UsuariosService } from './../../../services/usuarios/usuarios.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DatePipe } from '@angular/common'
import { MatDialog } from '@angular/material/dialog';
import { UsuariosConfirmacionComponent } from './../usuarios-confirmacion/usuarios-confirmacion.component';

@Component({
  selector: 'app-usuarios-registrar',
  templateUrl: './usuarios-registrar.component.html',
  styleUrls: ['./usuarios-registrar.component.css']
})
export class UsuariosRegistrarComponent implements OnInit {

  @Input() parametroPrueba: any;

  formulario: FormGroup;
  estadoList = ['S', 'N'];
  roles = [];
  usuario = {};
  bandera = 0;

  constructor(
    private formBuilder: FormBuilder,
    private usuariosService: UsuariosService,
    private datePipe: DatePipe,
    private matDialog: MatDialog,
    private dialogRef: MatDialogRef<UsuariosRegistrarComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any
  ) { 
    this.roles = data.data.roles;
    this.usuario = data.data.usuario;
  }

  async ngOnInit() {
    this.formulario = this.construirFormulario();
    this.llenarFormulario(this.usuario);
  }

  construirFormulario(): FormGroup {
    return this.formBuilder.group({
      nombre: new FormControl(null, [Validators.required]),
      rol_usuario: new FormControl(null, Validators.required),
      activo: new FormControl(null, Validators.required)
    });
  }

  get nombre() { return this.formulario.get('nombre'); }
  get rol_usuario() { return this.formulario.get('rol_usuario'); }
  get activo() { return this.formulario.get('activo'); }

  async guardar(){
    if(this.formulario.valid){
      const dialogRef = this.matDialog.open(UsuariosConfirmacionComponent, {
        width: '250px',
        height: '250px',
        data: { 
          data: {
            mensaje: this.bandera === 0 ? '¿Realmente desea crear este usuario?' : '¿Realmente desea actualizar los datos de este usuario?'
          }
        }
      });
  
      dialogRef.afterClosed().subscribe(async result => {
        if(result.resultado){
          if(this.bandera === 0){
            try{
              let fechaActual = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
              let usuario = {
                id_rol: this.buscarIdRol(this.rol_usuario.value),
                nombre: this.nombre.value,
                fecha_creacion: fechaActual,
                activo: this.activo.value
              };
              let resultado = await this.usuariosService.crearUsuario(usuario);
              if(resultado.codigo === 0){
                alert(`${resultado.mensaje}`);
                this.cancelar(true);
              }else{
                alert(`${resultado.mensaje}`);
              }
            }catch(error){
              alert(`Error insertando usuarios: ${error.error.detalle}`);
            }
          }else{
            try{
              let usuario = {
                id:  this.usuario[`id`],
                id_rol: this.buscarIdRol(this.rol_usuario.value),
                nombre: this.nombre.value,
                activo: this.activo.value
              };
              let resultado = await this.usuariosService.actualizarUsuario(usuario);
              if(resultado.codigo === 0){
                alert(`${resultado.mensaje}`);
                this.cancelar(true);
              }else{
                alert(`${resultado.mensaje}`);
              }
            }catch(error){
              alert(`Error actualizando usuarios: ${error.error.detalle}`);
            }
          }
        }
      });
      
    }else{
      alert('Error: Verifique todos los datos del formulario.'); 
    }
  }

  async modificar(usuario: any){
    try{
      let fechaActual = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
      let usuario = {
        id_rol: this.buscarIdRol(this.rol_usuario.value),
        nombre: this.nombre.value,
        fecha_creacion: fechaActual,
        activo: this.activo.value === 'SI' ? 'S' : 'N'
      };
      let resultado = await this.usuariosService.crearUsuario(usuario);
      if(resultado.codigo === 0){
        alert(`${resultado.mensaje}`);
        this.cancelar(true);
      }else{
        alert(`${resultado.mensaje}`);
      }
    }catch(error){
      alert(`Error insertando usuarios: ${error.error.detalle}`);
    }
  }

  cancelar(res: any){
    this.dialogRef.close({resultado: res}); 
  }

  buscarIdRol(nombreRol: any){
    let idRol = null;

    this.roles.forEach(element => {
      if(element.nombre === nombreRol){
        idRol = element.id;
      }
    });
    return idRol;
  }

  llenarFormulario(usuario: any){
    if(usuario !== null){
      this.nombre.setValue(usuario.nombre);
      this.rol_usuario.setValue(usuario.rol);
      this.activo.setValue(usuario.activo);
      this.bandera = 1;
    }else{
      this.bandera = 0;
    }
  }

}
