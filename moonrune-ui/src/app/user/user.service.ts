import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = "http://localhost:8080/user"
  httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})}

  constructor(private http: HttpClient) { }

  createUser(username: string, password: string) {
    let currUrl = this.url + "/" + username + "/" + password
    return this.http.post<User[]>(currUrl, this.httpOptions).pipe(catchError(this.handleError<User[]>('createUser', [])));
  }

  signIn(username: string, password: string) {
    let currUrl = this.url + "/" + username + "/" + password
    return this.http.get<User[]>(currUrl).pipe(catchError(this.handleError<User[]>('signIn', [])));
  }

  getUser(username: string) {
    let currUrl = this.url + "/" + username
    return this.http.get<User[]>(currUrl).pipe(catchError(this.handleError<User[]>('getUser', [])));
  }

  updateBio(username: string, bio: string) {
    let currUrl = this.url + "/" + username + "/" + bio
    return this.http.post<User[]>(currUrl, this.httpOptions).pipe(catchError(this.handleError<User[]>('updateBio', [])));
  }

  removeUser(username: string) {
    let currUrl = this.url + "/" + username
    return this.http.delete<boolean>(currUrl).pipe(catchError(this.handleError<boolean>('removeUser')));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }
}
