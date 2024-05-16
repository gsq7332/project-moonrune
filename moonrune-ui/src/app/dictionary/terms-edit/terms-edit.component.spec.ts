import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermsEditComponent } from './terms-edit.component';

describe('TermsEditComponent', () => {
  let component: TermsEditComponent;
  let fixture: ComponentFixture<TermsEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TermsEditComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TermsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
