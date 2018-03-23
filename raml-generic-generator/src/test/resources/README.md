# Test

Client and Request Builder for making API requests against [Test](https://api.example.com).

## Installation

```sh
composer require test/raml-php-sdk
```

## Usage

```php
namespace Test;

use Test\Client\ClientFactory;

require_once __DIR__ . '/vendor/autoload.php';

$client = ClientFactory::create();
```

### Authentication

#### OAuth 2.0

This API supports authentication with OAuth 2.0. Initialize the `OAuth2` instance with the application client id and client secret.

```php
use Test\Client\Config;
use Test\Client\ClientFactory;

$config = new Config();
$config->getCredentials()->setClientId('<client_id>')->setClientSecret('<client_secret>');

$client = ClientFactory::create($config);
```

#### Base URI

You can override the base URI by setting the `baseUri` property, or initializing a request builder with a base URI. For example:

```php
use Test\Client\Config;

$config = new Config();
$config->setApiUrl('http://google.com/search']);
```

### Mapper

To map the result data you can use the ResultMapper

```php
use Test\Base\ResultMapper;
use Test\Types\ModelClassMap;

$mapper = new ResultMapper(new ModelClassMap());
...
$response = $client->send($request);

// use one of the following possibilities
$result = $request->map($response, $mapper);
$result = $mapper->map($request, $response);
$result = $mapper->mapData(Project::class, json_decode((string)$response->getBody(), true));
```

### RequestBuilder

In order to be able to build request objects you can use the RequestBuilder. The following methods return a HTTP request instance of Guzzle [PSR-7](https://github.com/guzzle/psr7).

```php
use Test\Request\RequestBuilder;

$builder = new RequestBuilder();
```

Detailed information of all available methods can be found [here](docs/requestbuilder.md)

## License

MIT
