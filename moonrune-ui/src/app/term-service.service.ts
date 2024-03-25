import { ApplicationConfig, Injectable } from '@angular/core';
import { TERMS } from './terms/mock-term';
import { Observable, catchError, of } from 'rxjs';
import { Term } from './terms/term';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class TermServiceService {

  private url = 'http://localhost:8080/terms'; //link to api
  //httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})}
  constructor(private http: HttpClient) { }

  getTerms(): Observable<Term[]> {
    //return of(TERMS);
    
    return this.http.get<Term[]>(this.url)
    .pipe(catchError(this.handleError<Term[]>('getTerms', [])));
    //*/
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }

}
