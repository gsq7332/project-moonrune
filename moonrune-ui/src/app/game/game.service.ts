import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  hasStarted = false;

  constructor() { }

  checkStarted(): boolean {
    return this.hasStarted;
  }
  
  startGame(): boolean {
    this.hasStarted = true;
    return this.hasStarted;
  }

  endGame(): boolean {
    this.hasStarted = false;
    return this.hasStarted;
  }
}
