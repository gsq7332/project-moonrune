import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  hasStarted = false;

  constructor() { }

  checkStarted(): Observable<boolean> {
    return of(this.hasStarted);
  }
  
  startGame(): Observable<boolean> {
    this.hasStarted = true;
    return of(this.hasStarted);
  }

  endGame(): Observable<boolean> {
    this.hasStarted = false;
    return of(this.hasStarted);
  }
}
