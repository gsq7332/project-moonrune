import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
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
  
  ngOnInit(): void {
    
  }

  getIfStarted(): boolean {
    this.hasStarted = this.gameService.checkStarted();
    return this.hasStarted
  }
  
  initializeGame(): void {
    this.hasStarted = true;
    this.ngOnInit();
  }

  endGame(): void {
    this.hasStarted = false;
    this.ngOnInit();
  }
}
