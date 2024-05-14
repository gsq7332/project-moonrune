import { Component } from '@angular/core';
import { TermsListComponent } from '../list-view/terms-list/terms-list.component';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MainRoutingComponent } from '../../general/main-routing/main-routing.component';

@Component({
  selector: 'app-dictionary',
  standalone: true,
  imports: [TermsListComponent, RouterOutlet, RouterLink, RouterLinkActive, MainRoutingComponent],
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
