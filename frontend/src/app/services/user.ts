import {inject, Injectable} from '@angular/core';
import { CreateUserRequest, User as UserType } from '@common/types';
import {HttpClient} from '@angular/common/http';
import {environment} from '@environments/environment';

@Injectable({
  providedIn: 'root',
})
export class User {

  http = inject(HttpClient);

  createUser(request: CreateUserRequest) {
    const url = `${environment.apiUrl}:${environment.apiPort}${environment.apiPrefix}/users`;
    return this.http.post<UserType>(url, request);
  }
}
