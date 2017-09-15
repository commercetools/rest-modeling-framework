<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Base;

use Test\Client\ApiRequest;
use Psr\Http\Message\ResponseInterface;

interface Mapper
{
    public function mapData($class, $data);

    public function resolveDiscriminator($class, $discriminator, array $subTypes, array $data);
}
