import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UsuariosService } from './../../../services/usuarios/usuarios.service';
import { UsuariosRegistrarComponent } from './../usuarios-registrar/usuarios-registrar.component';
import { UsuariosConfirmacionComponent } from './../usuarios-confirmacion/usuarios-confirmacion.component';
import { DatePipe } from '@angular/common'

export interface PeriodicElement {
  nombre: any;
  fecha_creacion: any;
  rol: any;
  activo: any;
}

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  formulario: FormGroup;
  rolesList = [];
  usuariosList = [];
  displayedColumns: string[] = ['nombre', 'fecha_creacion', 'rol', 'activo','opciones'];
  pruebaField: any;

  constructor(
    private formBuilder: FormBuilder,
    private usuariosService: UsuariosService,
    private matDialog: MatDialog,
    private datePipe: DatePipe,
  ) { }

  async ngOnInit() {
    this.formulario = this.construirFormulario();
    await this.obtenerRoles();
  }

  construirFormulario(): FormGroup {
    return this.formBuilder.group({
      nombre_user: new FormControl(''),
      rol_user: new FormControl('')
    });
  }

  get nombre_user() { return this.formulario.get('nombre_user'); }
  get rol_user() { return this.formulario.get('rol_user'); }

  async consultarUsuarios(){
    try{
      let idRol = this.buscarIdRol(this.rol_user.value);
      let filtro = {
        idRol: idRol === '' ? null : idRol,
        nombre: this.nombre_user.value === null || this.nombre_user.value === '' ? null: this.nombre_user.value
      };
      this.usuariosList = await this.usuariosService.consultaUsuarios(filtro);
      this.usuariosList.forEach(element => {
        element.fecha_creacion =  this.datePipe.transform(element.fecha_creacion, 'yyyy-MM-dd');
        element['rol'] = this.buscarNombreRol(element.id_rol);
      });
    }catch(error){
      alert(`Error consultando usuarios: ${error.error.detalle}`);
    }
  }

  async desplegarRegistro(usuario: any = null){
    const dialogRef = this.matDialog.open(UsuariosRegistrarComponent, {
      width: '500px',
      height: '380px',
      data: { 
        data: {
          roles: this.rolesList,
          usuario: usuario
        }
      }
    });

    dialogRef.afterClosed().subscribe(async result => {
      if(result.resultado){
        await this.consultarUsuarios();
      }
    });
  }

  modificarUsuario(usuario: any){
    this.desplegarRegistro(usuario);
  }

  async eliminarUsuario(usuario: any){
    const dialogRef = this.matDialog.open(UsuariosConfirmacionComponent, {
      width: '250px',
      height: '250px',
      data: { 
        data: {
          mensaje: '¿Realmente desea eliminar este usuario?'
        }
      }
    });

    dialogRef.afterClosed().subscribe(async result => {
      if(result.resultado){
        try{
          let respuesta = await this.usuariosService.eliminarUsuario(usuario.id);
          alert(`${respuesta.mensaje}`);
          await this.consultarUsuarios();
        }catch(error){
          alert(`Error eliminando usuarios: ${error.error.detalle}`);
        }
      }
    });
  }

  async obtenerRoles(){
    try{
      this.rolesList = await this.usuariosService.consultaRoles();
    }catch(error){
      alert(`Error consultando roles: ${error.error.detalle}`);
    }
  }

  limpiar(){
    this.formulario.reset();
  }

  buscarIdRol(nombreRol: any){
    let idRol = '';

    this.rolesList.forEach(element => {
      if(element.nombre === nombreRol){
        idRol = element.id;
      }
    });
    return idRol;
  }

  buscarNombreRol(idRol: any){
    let nombreRol = '';

    this.rolesList.forEach(element => {
      if(element.id === idRol){
        nombreRol = element.nombre;
      }
    });
    return nombreRol;
  }

}
