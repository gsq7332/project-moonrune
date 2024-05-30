import { Injectable } from '@angular/core';
import { Term } from '../terms/term';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TermService {

  private url = 'http://localhost:8080/terms'; //link to api
  httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})}
  constructor(private http: HttpClient) { }

  updateTerms(collectionID: number, terms: Term[]) {
    let currUrl = this.url + "/updateTerms/" + collectionID
    return this.http.put<Term[]>(currUrl, terms)
    .pipe(catchError(this.handleError<Term[]>('checkIfOwner', [])));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }
}
