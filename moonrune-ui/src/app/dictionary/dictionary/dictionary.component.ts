import { Component } from '@angular/core';
import { ListEntryComponent } from '../list-view/list-entry/list-entry.component';
import { TermsListComponent } from '../list-view/terms-list/terms-list.component';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dictionary',
  standalone: true,
  imports: [TermsListComponent, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './dictionary.component.html',
  styleUrl: './dictionary.component.css'
})
export class DictionaryComponent {
  constructor(private route: ActivatedRoute) {}

  id ?: number = Number(this.route.snapshot.paramMap.get('id'))

  /*
  ngOnInit() {
    this.route.paramMap.pipe(switchMap(params => {
      this.id = parseInt(params.get('id')!, 10)
    }))
  }
  //*/
  
}
