<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObject;

interface Container extends JsonObject
{
    const FIELD_PATTERN0 = '//';

    public function get(string $key);
    public function set(string $key, $value);
}
