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
  
  startGame(numQuestions: number, numAnswers: number): Observable<number> {
    this.hasStarted = true;
    let currUrl = this.url + "/create/{$numQuestions}/{$numAnswers}";
    return this.http.post<number>(currUrl, HttpHeaders).pipe(catchError(this.handleError<number>('generateQuestion')));
  }

  setQuestion(id: number, questionType: string, answerType: string): Observable<boolean> {
    let currUrl = this.url + "/question/{$id}/{$questionType}/{$answerType}";
    return this.http.put<boolean>(currUrl, HttpHeaders).pipe(catchError(this.handleError<boolean>('generateQuestion')));
  }

  setTerms(id: number, collection: string): Observable<boolean> {
    let currUrl = this.url + '/term/{$id}/{$collection}';
    return this.http.put<boolean>(currUrl, HttpHeaders).pipe(catchError(this.handleError<boolean>('generateQuestion')));
  }

  generateQuestion(id: number): Observable<Term[]> {
    let currUrl = this.url + "/generate/{$id}";
    return this.http.get<Term[]>(currUrl).pipe(catchError(this.handleError<Term[]>('generateQuestion', [])));
  }

  getProgress(id: number): Observable<number[]> {
    let currUrl = this.url + "/{$id}";
    return this.http.get<number[]>(this.url)
    .pipe(catchError(this.handleError<number[]>('getProgress', [])));
  }

  checkQuestion(id: number, check: string): Observable<boolean> {
    let currUrl = this.url + "/check/{$id}/{$check}";
    return this.http.get<boolean>(currUrl).pipe(catchError(this.handleError<boolean>('checkQuestion')));
  }

  endGame(sessionID: number): Observable<boolean> {
    this.hasStarted = false;
    let currUrl = this.url + "/end/{$id}";
    this.http.delete<void>(currUrl).pipe(catchError(this.handleError<boolean>('checkQuestion')));
    return of(this.hasStarted);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    }
  }
}
