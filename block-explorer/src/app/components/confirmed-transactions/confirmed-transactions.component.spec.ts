import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmedTransactionsComponent } from './confirmed-transactions.component';

describe('ConfirmedTransactionsComponent', () => {
  let component: ConfirmedTransactionsComponent;
  let fixture: ComponentFixture<ConfirmedTransactionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmedTransactionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmedTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
