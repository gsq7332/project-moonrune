import { ApplicationConfig, Injectable } from '@angular/core';
import { Observable, catchError, of } from 'rxjs';
import { Term } from '../terms/term';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  hasStarted = false;
  url = "http://localhost:8080/game"


  constructor(private http: HttpClient) { }

  checkStarted(): Observable<boolean> {
    return of(this.hasStarted);
  }
  
  startGame(): Observable<boolean> {
    this.hasStarted = true;
    return of(this.hasStarted);
  }

  generateQuestion(id: number): Observable<Term[]> {
    let currUrl = this.url + "/generate";
    return this.http.get<Term[]>(currUrl).pipe(catchError(this.handleError<Term[]>('generateQuestion', [])));
  }

  getProgress(id: number): Observable<number[]> {
    return this.http.get<number[]>(this.url)
    .pipe(catchError(this.handleError<number[]>('getProgress', [])));
  }

  checkQuestion(id: number, check: string): Observable<boolean> {
    let currUrl = this.url + "/check";
    return this.http.get<boolean>(currUrl).pipe(catchError(this.handleError<boolean>('checkQuestion')));
  }

  endGame(): Observable<boolean> {
    this.hasStarted = false;
    return of(this.hasStarted);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }
}
