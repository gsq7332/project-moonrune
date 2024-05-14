import { ApplicationConfig, Injectable } from '@angular/core';
import { TERMS } from './terms/mock-term';
import { Observable, catchError, of } from 'rxjs';
import { Term } from './terms/term';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TermCollection } from './terms/termcollection';

@Injectable({
  providedIn: 'root'
})

export class TermServiceService {

  private url = 'http://localhost:8080/collections'; //link to api
  //httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})}
  constructor(private http: HttpClient) { }

  getTerms(collectionID: number): Observable<Term[]> {
    let currUrl = this.url + "/get/" + collectionID;
    return this.http.get<Term[]>(currUrl)
    .pipe(catchError(this.handleError<Term[]>('getTerms', [])));
    //*/
  }

  getCollectionsByOwner(owner: string): Observable<TermCollection[]> {
    let currUrl = this.url + "/collections/" + owner;
    return this.http.get<TermCollection[]>(currUrl)
    .pipe(catchError(this.handleError<TermCollection[]>('getCollectionsByOwner', [])));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }

}
