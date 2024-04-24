import { Component, Input, Output } from '@angular/core';
import { GameService } from '../game.service';
import { EventEmitter } from '@angular/core';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-game-settings',
  standalone: true,
  imports: [NgIf],
  templateUrl: './game-settings.component.html',
  styleUrl: './game-settings.component.css'
})
export class GameSettingsComponent {
  @Input() collection ?: string;
  @Output() startGameEvent = new EventEmitter();
  currentLevel: number = 1;
  /*
  level representations:
  0 - Default/Regular (used for user generated terms + Hangul)
  1 - Kanas (added Diacritics)
  2 - Cyrillic (added lowercase)
  3 - Greek (lowercase + name)
  4 - Kanji (readings)
  */

  constructor(private gameService: GameService) {}

  ngOnInit() {

  }

  startGame() {
    
    this.startGameEvent.emit("Starting game");
    //this.gameService.startGame();
  }
}