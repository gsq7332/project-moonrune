import { Component, Input, Output } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';

@Component({
  selector: 'app-game-settings',
  standalone: true,
  imports: [],
  templateUrl: './game-settings.component.html',
  styleUrl: './game-settings.component.css'
})
export class GameSettingsComponent {
  @Input() type ?: string;
  @Output() startGameEvent = new EventEmitter();

  constructor(private gameService: GameService) {}

  ngOnInit() {
    
  }

  startGame() {
    this.startGameEvent.emit("Starting game");
    //this.gameService.startGame();
  }
}