<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Request;

use Test\Base\Mapper;
use Test\Client\Resource;
use Test\Client\ApiRequest;

class RequestBuilder extends Resource
{
    public function __construct(array $args = [], Mapper $mapper = null)
    {
        parent::__construct('', $args, $mapper);
    }

    /**
     * @return Resource0
     */
    public function withProjectValue($project = null): Resource0 {
        $args = array_merge($this->getArgs(), array_filter(['project' => $project], function($value) { return !is_null($value); }));
        return new Resource0($this->getUri() . '/{project}', $args, $this->getMapper());
    }


    /**
     * @param string $method
     * @param string $uri
     * @param array $headers
     * @param $body
     * @param string $version
     * @return ApiRequest
     */
    final public function buildCustom(
        string $method,
        string $uri,
        array $headers = [],
        $body = null,
        string $version = '1.1'
    ): ApiRequest {
        return new ApiRequest($method, $uri, $headers, $body, $version);
    }

}
