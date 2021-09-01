import {
  Component,
  ElementRef,
  OnInit,
  ViewChild,
  Inject,
} from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';

gsap.registerPlugin(ScrollTrigger);


@Component({
  selector: 'app-features',
  templateUrl: './features.component.html',
  styleUrls: ['./features.component.css']
})
export class FeaturesComponent implements OnInit {
  @ViewChild('feature', { static: true }) feature: ElementRef<HTMLDivElement>;

  @ViewChild('featureConnected', { static: true }) featureConnected: ElementRef<HTMLDivElement>;

  @ViewChild('featureAnalysis', { static: true }) featureAnalysis: ElementRef<HTMLDivElement>;

  @ViewChild('privilege', { static: true }) privilege: ElementRef<HTMLDivElement>;

  constructor(@Inject(DOCUMENT) private document: Document) {};

  ngOnInit() {
    this.initScrollAnimations();
  }

  initScrollAnimations(): void {
    gsap.from(this.document.querySelector('.visual img'), {
      scrollTrigger: {
        trigger: this.document.querySelector('.visual img'),
        scrub: true,
        toggleClass: 'active',
        start: '-60% bottom',
      } as gsap.plugins.ScrollTriggerInstanceVars,
      duration: 1.0,
      height: 0,
      opacity: 0,
    });


    gsap.from(this.document.querySelector('.text .heading-2'), {
      scrollTrigger: {
        trigger: this.document.querySelector('.text .heading-2'),
        scrub: true,
        toggleClass: 'active',
        start: '-60% bottom',
      } as gsap.plugins.ScrollTriggerInstanceVars,
      duration: 1.5,
      y: 40,
      opacity: 0,
    });

    gsap.from(this.document.querySelector('.text .paragraph'), {
      scrollTrigger: {
        trigger: this.document.querySelector('.text .paragraph'),
        scrub: true,
        toggleClass: 'active',
        start: '-60% bottom',
      } as gsap.plugins.ScrollTriggerInstanceVars,
      duration: 1.5,
      y: 40,
      opacity: 0,
    });

    //feature connected
    gsap.from(this.document.querySelector('.featureConnected .animation .visual img'), {
      scrollTrigger: {
        trigger: this.document.querySelector('.featureConnected .animation .visual img'),
        scrub: true,
        toggleClass: 'active',
        start: '-60% bottom',
      } as gsap.plugins.ScrollTriggerInstanceVars,
      duration: 1.5,
      height: 0,
      opacity: 0,
    });


    gsap.from(this.document.querySelector('.featureConnected .text .heading-2'), {
      scrollTrigger: {
        trigger: this.document.querySelector('.featureConnected .text .heading-2'),
        scrub: true,
        toggleClass: 'active',
        start: '-60% bottom',
      } as gsap.plugins.ScrollTriggerInstanceVars,
      duration: 1.5,
      y: 40,
      opacity: 0,
    });
    gsap.from(this.document.querySelector('.featureConnected .text .paragraph'), {
      scrollTrigger: {
        trigger: this.document.querySelector('.featureConnected .text .paragraph'),
        scrub: true,
        toggleClass: 'active',
        start: '-60% bottom',
      } as gsap.plugins.ScrollTriggerInstanceVars,
      duration: 1.5,
      y: 40,
      opacity: 0,
    });

    // analysis
    gsap.from(this.document.querySelector('.featureAnalysis .animation .visual img'), {
      scrollTrigger: {
        trigger: this.document.querySelector('.featureAnalysis .animation .visual img'),
        scrub: true,
        toggleClass: 'active',
        start: '-60% bottom',
      } as gsap.plugins.ScrollTriggerInstanceVars,
      duration: 1.5,
      height: 0,
      opacity: 0,
    });


    gsap.from(this.document.querySelector('.featureAnalysis .text .heading-2'), {
      scrollTrigger: {
        trigger: this.document.querySelector('.featureAnalysis .text .heading-2'),
        scrub: true,
        toggleClass: 'active',
        start: '-60% bottom',
      } as gsap.plugins.ScrollTriggerInstanceVars,
      duration: 1.5,
      y: 40,
      opacity: 0,
    });
    gsap.from(this.document.querySelector('.featureAnalysis .text .paragraph'), {
      scrollTrigger: {
        trigger: this.document.querySelector('.featureAnalysis .text .paragraph'),
        scrub: true,
        toggleClass: 'active',
        start: '-60% bottom',
      } as gsap.plugins.ScrollTriggerInstanceVars,
      duration: 1.5,
      y: 40,
      opacity: 0,
    });
  }
}
