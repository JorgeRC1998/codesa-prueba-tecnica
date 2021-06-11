import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuariosConfirmacionComponent } from './usuarios-confirmacion.component';

describe('UsuariosConfirmacionComponent', () => {
  let component: UsuariosConfirmacionComponent;
  let fixture: ComponentFixture<UsuariosConfirmacionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsuariosConfirmacionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsuariosConfirmacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
