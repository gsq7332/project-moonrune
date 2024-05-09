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
  httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json'})}

  constructor(private http: HttpClient) { }

  checkStarted(): Observable<boolean> {
    return of(this.hasStarted);
  }
  
  startGame(numQuestions: number, numAnswers: number): Observable<number> {
    this.hasStarted = true;
    let currUrl = this.url + "/create/" + numQuestions + "/" + numAnswers;
    return this.http.post<number>(currUrl, this.httpOptions).pipe(catchError(this.handleError<number>('generateQuestion')));
  }

  setQuestion(id: number, questionType: string, answerType: string): Observable<boolean> {
    let currUrl = this.url + "/question/" + id + "/" + questionType + "/" + answerType;
    return this.http.put<boolean>(currUrl, this.httpOptions).pipe(catchError(this.handleError<boolean>('generateQuestion')));
  }

  setTerms(id: number, collection: number): Observable<boolean> {
    let currUrl = this.url + "/term/" + id + "/" + collection;
    return this.http.put<boolean>(currUrl, this.httpOptions).pipe(catchError(this.handleError<boolean>('generateQuestion')));
  }

  generateQuestion(id: number): Observable<string[]> {
    let currUrl = this.url + "/generate/" + id;
    return this.http.get<string[]>(currUrl).pipe(catchError(this.handleError<string[]>('generateQuestion', [])));
  }

  getProgress(id: number): Observable<number[]> {
    let currUrl = this.url + "/" + id;
    return this.http.get<number[]>(currUrl)
    .pipe(catchError(this.handleError<number[]>('getProgress', [])));
  }

  checkQuestion(id: number, check: string): Observable<boolean> {
    let currUrl = this.url + "/check/" + id + "/" + check;
    return this.http.get<boolean>(currUrl).pipe(catchError(this.handleError<boolean>('checkQuestion')));
  }

  checkActivity(id: number): Observable<boolean> {
    let currUrl = this.url + "/active/" + id
    return this.http.get<boolean>(currUrl).pipe(catchError(this.handleError<boolean>('checkActivity')));
  }

  endGame(id: number): Observable<boolean> {
    if (id > -1) {
      let currUrl = this.url + "/end/" + id;
      this.http.delete<void>(currUrl).pipe(catchError(this.handleError<void>('endGame')));
    }
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
