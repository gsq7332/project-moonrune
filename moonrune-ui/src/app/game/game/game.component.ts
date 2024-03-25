import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { GameSettingsComponent } from '../game-settings/game-settings.component';
import { MultipleChoiceComponent } from '../multiple-choice/multiple-choice.component';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [NgIf, GameSettingsComponent, MultipleChoiceComponent],
  templateUrl: './game.component.html',
  styleUrl: './game.component.css'
})
export class GameComponent {

  hasStarted = false;
  
  ngOnInit(): void {
    
  }
  
  initializeGame(): void {
    this.hasStarted = true;
  }

  endGame(): void {
    this.hasStarted = false;
  }
}
