// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

import '@vaadin/vaadin-charts/theme/vaadin-chart-default-theme';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
<custom-style>
  <style>
    html {
      --lumo-primary-color: hsl(156,100%,18%);
      --lumo-primary-text-color: hsl(156,100%,18%);
    }

    [theme~="dark"] {
      --lumo-shade-5pct: rgba(0, 101, 2, 0.05);
      --lumo-shade-10pct: rgba(0, 101, 2, 0.1);
      --lumo-shade-20pct: rgba(0, 101, 2, 0.2);
      --lumo-shade-30pct: rgba(0, 101, 2, 0.3);
      --lumo-shade-40pct: rgba(0, 101, 2, 0.4);
      --lumo-shade-50pct: rgba(0, 101, 2, 0.5);
      --lumo-shade-60pct: rgba(0, 101, 2, 0.6);
      --lumo-shade-70pct: rgba(0, 101, 2, 0.7);
      --lumo-shade-80pct: rgba(0, 101, 2, 0.8);
      --lumo-shade-90pct: rgba(0, 101, 2, 0.9);
      --lumo-primary-color-50pct: rgba(235,89,5,0.5);
      --lumo-primary-color-10pct: rgba(235, 89, 5, 0.1);
      --lumo-error-color-50pct: rgba(231, 24, 24, 0.5);
      --lumo-error-color-10pct: rgba(231, 24, 24, 0.1);
      --lumo-success-color-50pct: rgba(62,229,115,0.5);
      --lumo-success-color-10pct: rgba(62,229,115,0.1);
      --lumo-shade: hsl(125,68%,11%); 
      /*0, 0%, 13%*/
      --lumo-primary-color: hsl(22,100%,42%);
      --lumo-primary-text-color: hsl(22, 100%, 42%);
      --lumo-error-color: hsl(0, 100%, 31%);
      --lumo-error-text-color: hsl(0, 100%, 31%);
      --lumo-success-color: hsl(141,76%,57%);
      --lumo-success-contrast-color: hsl(125,68%,11%);
      --lumo-success-text-color: hsl(125,68%,11%);
      --lumo-base-color: hsl(140,100%,15%);
      --lumo-body-text-color: hsla(0,0%,100%,0.9);
    }
  </style>
</custom-style>


<custom-style>
  <style>
    html {
      overflow:hidden;
    }
    vaadin-app-layout vaadin-tab a:hover {
      text-decoration: none;
    }
    h1 {
      color: hsl(140,100%,14%);
      }
  </style>
</custom-style>

<dom-module id="chart" theme-for="vaadin-chart">
  <template>
    <style include="vaadin-chart-default-theme">
      :host {
        --vaadin-charts-color-0: var(--lumo-primary-color);
        --vaadin-charts-color-1: var(--lumo-error-color);
        --vaadin-charts-color-2: var(--lumo-success-color);
        --vaadin-charts-color-3: var(--lumo-contrast);
      }
      .highcharts-container {
        font-family: var(--lumo-font-family);
      }
      .highcharts-background {
        fill: var(--lumo-base-color);
      }
      .highcharts-title {
        fill: var(--lumo-header-text-color);
        font-size: var(--lumo-font-size-xl);
        font-weight: 600;
        line-height: var(--lumo-line-height-xs);
      }
      .highcharts-legend-item text {
        fill: var(--lumo-body-text-color);
      }
      .highcharts-axis-title,
      .highcharts-axis-labels {
        fill: var(--lumo-secondary-text-color);
      }
      .highcharts-axis-line,
      .highcharts-grid-line,
      .highcharts-tick {
        stroke: var(--lumo-contrast-10pct);
      }
      .highcharts-column-series rect.highcharts-point {
        stroke: var(--lumo-base-color);
      }
    </style>
    <style>
            [part=icon]::before {
            /* Use a custom font icon or background image */
        }
        </style>
  </template>
</dom-module>`;



document.head.appendChild($_documentContainer.content);
