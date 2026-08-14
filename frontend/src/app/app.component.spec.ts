import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [provideHttpClient(), provideHttpClientTesting()]
    }).compileComponents();

    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('devrait créer le composant', () => {
    const fixture = TestBed.createComponent(AppComponent);
    expect(fixture.componentInstance).toBeTruthy();
  });

  it('devrait afficher le texte inversé renvoyé par l\'API', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;

    app.text = 'bonjour';
    app.send();

    const req = httpMock.expectOne('/api/reverse');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ text: 'bonjour' });

    req.flush({ original: 'bonjour', reversed: 'ruojnob' });

    expect(app.reversed).toBe('ruojnob');
    expect(app.hasResult).toBeTrue();
    expect(app.error).toBe('');
  });
});
