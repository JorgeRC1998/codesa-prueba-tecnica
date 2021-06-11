import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})

export class UsuariosService {

  constructor(private httpClient: HttpClient) {
  }

  async consultaRoles(): Promise<any> {
    const url = environment.urlBackendPruebaTecnica + `/prueba/roles`;
    return await this.httpClient.get(url).toPromise();
  }

  async consultaUsuarios(filtro: any): Promise<any> {
    const url = environment.urlBackendPruebaTecnica + `/prueba/usuarios`;
    return await this.httpClient.post(url, filtro).toPromise();
  }

  async crearUsuario(filtro: any): Promise<any> {
    const url = environment.urlBackendPruebaTecnica + `/prueba/usuario`;
    return await this.httpClient.post(url, filtro).toPromise();
  }

  async actualizarUsuario(filtro: any): Promise<any> {
    const url = environment.urlBackendPruebaTecnica + `/prueba/usuario`;
    return await this.httpClient.put(url, filtro).toPromise();
  }

  async eliminarUsuario(idUsuario: any): Promise<any> {
    const url = environment.urlBackendPruebaTecnica + `/prueba/usuario/${idUsuario}`;
    return await this.httpClient.delete(url).toPromise();
  }

}
