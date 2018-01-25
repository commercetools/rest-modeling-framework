<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\ClassMapModel;
use Test\Base\Collection;
use Test\Base\JsonCollection;
use Test\Base\JsonObject;
use Test\Base\JsonObjectModel;
use Test\Types;

class ModelClassMap extends ClassMapModel
{
    protected static $types = [
        JsonObject::class => JsonObjectModel::class,
        Collection::class => JsonCollection::class,
        Types\Address::class => Types\AddressModel::class,
        Types\Customer::class => Types\CustomerModel::class,

    ];
}
