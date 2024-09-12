function loadApp(appType) {
  let appContent = document.getElementById('appContent');
  appContent.innerHTML = '';

  switch (appType) {
    case 'currency':
      appContent.innerHTML = `
        <h2>Currency Converter</h2>
        <input type="number" id="amount" placeholder="Amount" />
        <input type="text" id="source" placeholder="Source Currency (e.g., USD)" />
        <input type="text" id="target" placeholder="Target Currency (e.g., EUR)" />
        <button onclick="convertCurrency()">Convert</button>
        <p id="result"></p>
      `;
      break;

    case 'weather':
      appContent.innerHTML = `
        <h2>Weather Forecaster</h2>
        <input type="text" id="city" placeholder="City Name" />
        <button onclick="getWeather()">Get Weather</button>
        <p id="result"></p>
      `;
      break;

    case 'sentiment':
      appContent.innerHTML = `
        <h2>Text Sentiment Analyzer</h2>
        <textarea id="text" placeholder="Enter text"></textarea>
        <button onclick="analyzeSentiment()">Analyze Sentiment</button>
        <p id="result"></p>
      `;
      break;

    case 'stock':
      appContent.innerHTML = `
        <h2>Stock Price Tracker</h2>
        <input type="text" id="ticker" placeholder="Stock Ticker (e.g., AAPL)" />
        <button onclick="getStockPrice()">Get Stock Price</button>
        <p id="result"></p>
      `;
      break;

    case 'unit':
      appContent.innerHTML = `
        <h2>Unit Converter</h2>
        <input type="number" id="value" placeholder="Value" />
        <input type="text" id="sourceUnit" placeholder="Source Unit" />
        <input type="text" id="targetUnit" placeholder="Target Unit" />
        <button onclick="convertUnit()">Convert</button>
        <p id="result"></p>
      `;
      break;

    case 'expense':
      appContent.innerHTML = `
        <h2>Personal Expense Tracker</h2>
        <input type="number" id="expense" placeholder="Expense Amount" />
        <input type="text" id="category" placeholder="Category (e.g., Food)" />
        <input type="date" id="date" />
        <button onclick="addExpense()">Add Expense</button>
        <p id="result"></p>
      `;
      break;
  }
}

function convertCurrency() {
  let amount = document.getElementById('amount').value;
  let source = document.getElementById('source').value;
  let target = document.getElementById('target').value;
  
  if (!amount || !source || !target) {
    document.getElementById('result').innerText = 'Please provide all inputs.';
    return;
  }

  fetch(`https://api.exchangerate-api.com/v4/latest/${source}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      let rate = data.rates[target];
      if (rate === undefined) {
        throw new Error('Invalid target currency');
      }
      let result = amount * rate;
      document.getElementById('result').innerText = `${amount} ${source} = ${result.toFixed(2)} ${target}`;
    })
    .catch(error => {
      console.error('Error:', error);
      document.getElementById('result').innerText = 'Error fetching data.';
    });
}

function getWeather() {
  let city = document.getElementById('city').value;

  if (!city) {
    document.getElementById('result').innerText = 'Please enter a city name.';
    return;
  }

  fetch(`https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=YOUR_API_KEY&units=metric`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      let weather = data.weather[0].description;
      let temp = data.main.temp;
      document.getElementById('result').innerText = `Weather: ${weather}, Temperature: ${temp}°C`;
    })
    .catch(error => {
      console.error('Error:', error);
      document.getElementById('result').innerText = 'Error fetching data.';
    });
}

function analyzeSentiment() {
  let text = document.getElementById('text').value;

  if (!text) {
    document.getElementById('result').innerText = 'Please enter some text.';
    return;
  }

  fetch('https://sentiment-analysis-api.example.com/analyze', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ text: text })
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      document.getElementById('result').innerText = `Sentiment: ${data.sentiment}, Confidence: ${data.confidence}`;
    })
    .catch(error => {
      console.error('Error:', error);
      document.getElementById('result').innerText = 'Error fetching data.';
    });
}

function getStockPrice() {
  let ticker = document.getElementById('ticker').value;

  if (!ticker) {
    document.getElementById('result').innerText = 'Please enter a stock ticker.';
    return;
  }

  fetch(`https://api.stockprice.example.com/price?ticker=${ticker}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      document.getElementById('result').innerText = `Price: ${data.price}, Trend: ${data.trend}`;
    })
    .catch(error => {
      console.error('Error:', error);
      document.getElementById('result').innerText = 'Error fetching data.';
    });
}

function convertUnit() {
  let value = document.getElementById('value').value;
  let sourceUnit = document.getElementById('sourceUnit').value;
  let targetUnit = document.getElementById('targetUnit').value;

  if (!value || !sourceUnit || !targetUnit) {
    document.getElementById('result').innerText = 'Please provide all inputs.';
    return;
  }

  // Unit conversion logic
  let conversionFactor = getConversionFactor(sourceUnit, targetUnit);
  
  if (conversionFactor === null) {
    document.getElementById('result').innerText = 'Invalid unit conversion.';
    return;
  }

  let convertedValue = value * conversionFactor;

  document.getElementById('result').innerText = `${value} ${sourceUnit} = ${convertedValue} ${targetUnit}`;
}

// Function to provide conversion factor between units
function getConversionFactor(source, target) {
  const conversionTable = {
    'kilometers_miles': 0.621371,
    'miles_kilometers': 1.60934,
    'meters_feet': 3.28084,
    'feet_meters': 0.3048,
    'liters_gallons': 0.264172,
    'gallons_liters': 3.78541,
    'kilograms_pounds': 2.20462,
    'pounds_kilograms': 0.453592
  };

  let key = `${source}_${target}`;
  return conversionTable[key] || null; // Return null if no matching conversion
}


function addExpense() {
  let expense = document.getElementById('expense').value;
  let category = document.getElementById('category').value;
  let date = document.getElementById('date').value;

  if (!expense || !category || !date) {
    document.getElementById('result').innerText = 'Please provide all inputs.';
    return;
  }

  // For simplicity, we are not sending data to a server. You can implement server-side storage if needed.
  document.getElementById('result').innerText = `Expense added: ${expense} in ${category} on ${date}`;
}
