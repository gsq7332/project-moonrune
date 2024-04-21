import { NgIf } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { GameSettingsComponent } from '../game-settings/game-settings.component';
import { MultipleChoiceComponent } from '../multiple-choice/multiple-choice.component';
import { GameService } from '../game.service';


@Component({
  selector: 'app-game',
  standalone: true,
  imports: [NgIf, GameSettingsComponent, MultipleChoiceComponent],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent {


  constructor(private gameService: GameService) {}

  hasStarted = false;
  hasEnded = false;
  sessionID: number = -1;

  
  ngOnInit(): void {
    this.hasStarted = this.getIfStarted();
  }

  getIfStarted(): boolean {
    this.gameService.checkStarted().subscribe(hasStarted => this.hasStarted = hasStarted);
    return this.hasStarted
  }
  
  initializeGame(): void {
    this.gameService.startGame(10, 4, "term", "meanings").subscribe(id => this.sessionID = id);
    this.gameService.checkStarted().subscribe(isStarting => this.hasStarted = isStarting);
  }

  endGame(): void {
    this.gameService.endGame(this.sessionID).subscribe(hasStarted => this.hasStarted = hasStarted);
  }
}
