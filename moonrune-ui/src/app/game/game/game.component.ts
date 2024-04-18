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

  
  ngOnInit(): void {
    this.hasStarted = this.getIfStarted();
  }

  getIfStarted(): boolean {
    this.gameService.checkStarted().subscribe(hasStarted => this.hasStarted = hasStarted);
    return this.hasStarted
  }

  toggleGame(isStarting: boolean): void {
    
  }
  
  initializeGame(): void {
    this.gameService.startGame().subscribe(hasStarted => this.hasStarted = hasStarted);
  }

  endGame(): void {
    this.gameService.endGame().subscribe(hasStarted => this.hasStarted = hasStarted);
  }
}
