import { Component, Input, SimpleChanges } from '@angular/core';
import { Term } from '../../terms/term';
import { Cyrillic } from '../../terms/cyrillic';
import { Greek } from '../../terms/greek';
import { Kanji } from '../../terms/kanji';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-term-details',
  standalone: true,
  imports: [NgIf, FormsModule],
  templateUrl: './term-details.component.html',
  styleUrl: './term-details.component.css'
})
export class TermDetailsComponent {
  @Input() term?: Term;
  
  isCyrillic : boolean = false;
  isGreek : boolean = false;
  isKanji : boolean = false;
  cyrillicTerm ?: Cyrillic;
  greekTerm ?: Greek;
  kanjiTerm ?: Kanji;

  ngOnChanges(changes : SimpleChanges) {
    if (changes['term']) {
      if (this.term != null && this.term != undefined) {
        this.isCyrillic = ('lower' in this.term) && !('name' in this.term);
        this.isGreek = ('lower' in this.term) && ('name' in this.term);
        this.isKanji = 'readings' in this.term;

        if (this.isCyrillic) {
          this.cyrillicTerm = this.term as Cyrillic;
        } else if (this.isGreek) {
          this.greekTerm = this.term as Greek;
        } else if (this.isKanji) {
          this.kanjiTerm = this.term as Kanji;
        }
      }
    }
  }

}
